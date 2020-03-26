package gameobjects

import GameObject
import Mesh
import vision.gears.webglmath.Vec2
import vision.gears.webglmath.Vec3
import kotlin.math.roundToInt

class Boom(
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        scale : Vec3 = Vec3(0.1f, 0.1f, 0.0f),
        mesh: Mesh = Mesh.get("boom")
        ) : GameObject(mesh, position, roll, scale) {

    init {
        addComponentsAndGatherUniforms(mesh)
        size.set(1.0f / 6.0f, 1.0f / 6.0f)
    }

    var phase = 0.0f;

    override fun interact(
            dt : Float,
            t : Float,
            keysPressed : Set<String>,
            gameObjects : ArrayList<GameObject>,
            mousePosition : Vec2,
            gameObjectsToAdd : ArrayList<GameObject>
    ) : Boolean {
        if (phase >= 33.0f)
            return false;
        offset.x = (phase.roundToInt() % 6).toFloat() / 6.0f
        offset.y = (phase.roundToInt() / 6).toFloat() / 6.0f

        phase += dt * 15.0f;
        return true;
    }
}