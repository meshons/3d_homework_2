
import gameobjects.SpaceShipAvatar
import meshes.SpaceShipMesh
import org.w3c.dom.HTMLCanvasElement
import vision.gears.webglmath.UniformProvider
import kotlin.js.Date
import org.khronos.webgl.WebGLRenderingContext as GL

class Scene (
  val gl : WebGL2RenderingContext) : UniformProvider("scene") {

  val vsTrafo = Shader(gl, GL.VERTEX_SHADER, "shaders/trafo-vs.glsl")
  val fsTextured = Shader(gl, GL.FRAGMENT_SHADER, "shaders/textured-fs.glsl")
  val texturedProgram = Program(gl, vsTrafo, fsTextured)
  val quadGeometry = TexturedQuadGeometry(gl)

  val spaceShipMesh = SpaceShipMesh(gl, texturedProgram, quadGeometry)
  val spaceShipAvatar = SpaceShipAvatar(spaceShipMesh)

  val gameObjects = ArrayList<GameObject>()

  val timeAtFirstFrame = Date().getTime()
  var timeAtLastFrame =  timeAtFirstFrame

  val camera = OrthoCamera(*Program.all)

  init{
    gl.enable(GL.BLEND)
    gl.blendFunc( GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA)

    gameObjects.add(spaceShipAvatar)

    addComponentsAndGatherUniforms(*Program.all)
  }

  fun resize(gl : WebGL2RenderingContext, canvas : HTMLCanvasElement) {
    gl.viewport(0, 0, canvas.width, canvas.height)
    camera.setAspectRatio((canvas.width/canvas.height).toFloat())
  }

  @Suppress("UNUSED_PARAMETER")
  fun update(gl : WebGL2RenderingContext, keysPressed : Set<String>) {

    val dt = (Date().getTime().toFloat() - timeAtLastFrame.toFloat()) / 1000.0f
    val t  = (Date().getTime().toFloat() - timeAtFirstFrame.toFloat()) / 1000.0f    

    gl.clearColor(0.0f, 0.0f, 0.0f, 1.0f)
    gl.clearDepth(1.0f)
    gl.clear(GL.COLOR_BUFFER_BIT or GL.DEPTH_BUFFER_BIT)

    gameObjects.forEach { it.move(dt, t, keysPressed, gameObjects) }
    gameObjects.forEach { it.update() }
    camera.position.set(spaceShipAvatar.position.xy)
    camera.updateViewProjMatrix()
    gameObjects.forEach { it.draw(camera) }

    timeAtLastFrame = Date().getTime()
  }
}
