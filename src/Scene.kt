
import gameobjects.Background
import gameobjects.MeteorBig
import gameobjects.SpaceShipAvatar
import meshes.*
import org.w3c.dom.HTMLCanvasElement
import vision.gears.webglmath.UniformProvider
import vision.gears.webglmath.Vec2
import kotlin.js.Date
import org.khronos.webgl.WebGLRenderingContext as GL

class Scene (
  val gl : WebGL2RenderingContext) : UniformProvider("scene") {

  val vsTrafo = Shader(gl, GL.VERTEX_SHADER, "shaders/trafo-vs.glsl")
  val vsBackground = Shader(gl, GL.VERTEX_SHADER, "shaders/background-vs.glsl")

  val fsTextured = Shader(gl, GL.FRAGMENT_SHADER, "shaders/textured-fs.glsl")
  val fsBackground = Shader(gl, GL.FRAGMENT_SHADER, "shaders/background-fs.glsl")

  val texturedProgram = Program(gl, vsTrafo, fsTextured)
  val backgroundProgram = Program(gl, vsBackground, fsBackground)

  val quadGeometry = TexturedQuadGeometry(gl)

  val spaceShipMeshArray = arrayListOf(
          SpaceShipMesh2(gl, texturedProgram, quadGeometry)
  )
  val backgroundMesh = BackgroundMesh(gl, backgroundProgram, quadGeometry)
  val meteorBigMeshArray = arrayListOf(
      MeteorBigMesh1(gl, texturedProgram, quadGeometry)
  )
  val laserMesh = LaserMesh(gl, texturedProgram, quadGeometry)

  val spaceShipAvatar = SpaceShipAvatar(
          SpaceShipMesh1(gl, texturedProgram, quadGeometry),
          laserMesh
  )

  val gameObjects = ArrayList<GameObject>()

  val timeAtFirstFrame = Date().getTime()
  var timeAtLastFrame =  timeAtFirstFrame

  val camera = OrthoCamera(*Program.all)

  init{
    gl.enable(GL.BLEND)
    gl.blendFunc( GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA)

    gameObjects.add(Background(backgroundMesh))
    gameObjects.add(spaceShipAvatar)
    gameObjects.add(MeteorBig(meteorBigMeshArray[0]))

    addComponentsAndGatherUniforms(*Program.all)
  }

  fun resize(gl : WebGL2RenderingContext, canvas : HTMLCanvasElement) {
    gl.viewport(0, 0, canvas.width, canvas.height)
    camera.setAspectRatio(canvas.width.toFloat()/canvas.height.toFloat())
  }

  @Suppress("UNUSED_PARAMETER")
  fun update(gl : WebGL2RenderingContext, keysPressed : Set<String>, mousePosition : Vec2) {
    val dt = (Date().getTime().toFloat() - timeAtLastFrame.toFloat()) / 1000.0f
    val t  = (Date().getTime().toFloat() - timeAtFirstFrame.toFloat()) / 1000.0f    

    gl.clearColor(0.0f, 0.0f, 0.0f, 1.0f)
    gl.clearDepth(1.0f)
    gl.clear(GL.COLOR_BUFFER_BIT or GL.DEPTH_BUFFER_BIT)

    val gameObjectsToAdd = ArrayList<GameObject>();

    gameObjects.forEach {
      if(!it.interact(dt, t, keysPressed, gameObjects, mousePosition, gameObjectsToAdd))
        gameObjects.remove(it)
    }
    gameObjects.forEach { it.update() }
    camera.position.set(spaceShipAvatar.position.xy)
    camera.updateViewProjMatrix()
    gameObjects.forEach { it.draw(camera) }

    gameObjects.addAll(gameObjectsToAdd)

    timeAtLastFrame = Date().getTime()
  }
}
