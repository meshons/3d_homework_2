
import gameobjects.Background
import gameobjects.MeteorBig
import gameobjects.MeteorMed
import gameobjects.SpaceShipAvatar
import meshes.BackgroundMesh
import meshes.BoomMesh
import meshes.LaserMesh
import meshes.SpaceShipMesh
import meshes.meteor.*
import org.w3c.dom.HTMLCanvasElement
import vision.gears.webglmath.UniformProvider
import vision.gears.webglmath.Vec2
import vision.gears.webglmath.Vec3
import kotlin.js.Date
import kotlin.math.PI
import kotlin.random.Random
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

  val spaceShipAvatar = SpaceShipAvatar(SpaceShipMesh(gl, texturedProgram, quadGeometry))

  val gameObjects = ArrayList<GameObject>()

  val timeAtFirstFrame = Date().getTime()
  var timeAtLastFrame =  timeAtFirstFrame

  val camera = OrthoCamera(*Program.all)

  init{
    gl.enable(GL.BLEND)
    gl.blendFunc( GL.SRC_ALPHA, GL.ONE_MINUS_SRC_ALPHA)

    Mesh.add("background", BackgroundMesh(gl, backgroundProgram, quadGeometry))

    Mesh.add("meteor_big_1", MeteorBigMesh1(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_big_2", MeteorBigMesh2(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_big_3", MeteorBigMesh3(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_big_4", MeteorBigMesh4(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_big_5", MeteorBigMesh5(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_big_6", MeteorBigMesh6(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_big_7", MeteorBigMesh7(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_big_8", MeteorBigMesh8(gl, texturedProgram, quadGeometry))

    Mesh.add("meteor_med_1", MeteorMedMesh1(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_med_2", MeteorMedMesh2(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_med_3", MeteorMedMesh3(gl, texturedProgram, quadGeometry))
    Mesh.add("meteor_med_4", MeteorMedMesh4(gl, texturedProgram, quadGeometry))

    Mesh.add("laser", LaserMesh(gl, texturedProgram, quadGeometry))
    Mesh.add("boom", BoomMesh(gl, texturedProgram, quadGeometry))

    gameObjects.add(Background(Mesh.get("background")))

    val randomCoord : () -> Float = { (Random.nextFloat() - 0.5f) * 6.0f }

    var randomPosition : () -> Vec3 = {
      Vec3(randomCoord(), randomCoord(), 0.0f)
    }

    for (i in 0..10) {
      if (Random.nextBoolean())
        gameObjects.add(
                MeteorBig(
                        Mesh.get("meteor_big_" + Random.nextInt(1, 9)),
                        randomPosition(),
                        Random.nextFloat() % (PI.toFloat() * 2)
                )
        )
      else
        gameObjects.add(
                MeteorMed(
                        Mesh.get("meteor_med_" + Random.nextInt(1, 5)),
                        randomPosition(),
                        Random.nextFloat() % (PI.toFloat() * 2)
                )
        )
    }

    gameObjects.add(spaceShipAvatar)

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
    val gameObjectsToRemove = ArrayList<GameObject>();

    gameObjects.forEach {
      if(!it.interact(dt, t, keysPressed, gameObjects, mousePosition, gameObjectsToAdd))
        gameObjectsToRemove.add(it)
    }
    gameObjects.forEach { it.update() }
    camera.position.set(spaceShipAvatar.position.xy)
    camera.updateViewProjMatrix()
    gameObjects.forEach { it.draw(camera) }

    gameObjects.addAll(gameObjectsToAdd)
    gameObjects.removeAll(gameObjectsToRemove)

    timeAtLastFrame = Date().getTime()
  }
}
