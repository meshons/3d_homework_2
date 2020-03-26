import vision.gears.webglmath.Mat4
import vision.gears.webglmath.UniformProvider
import vision.gears.webglmath.Vec2

class OrthoCamera(vararg programs : Program) : UniformProvider("camera") {
  val position = Vec2(0.0f, 0.0f)
  val roll = 0.0f
  val windowSize = Vec2(2.0f, 2.0f)

  val viewProjMatrix by Mat4()
  val invViewProjMatrix by Mat4()
  val invViewProjMatrix2 by Mat4()

  init{
    updateViewProjMatrix()
    addComponentsAndGatherUniforms(*programs)
  }

  fun updateViewProjMatrix() {
    viewProjMatrix
            .set()
            .scale(windowSize)
            .rotate(roll)
            .translate(position)
            .invert()
    invViewProjMatrix
            .set()
            .scale(windowSize)
            .rotate(roll)
            .translate(position)
    invViewProjMatrix2
            .set()
            .scale(windowSize)
            .rotate(roll)
            .translate(position / 1.5f)
  }

  fun setAspectRatio(ar : Float) {
    windowSize.x = windowSize.y * ar
    updateViewProjMatrix()
  }
}
