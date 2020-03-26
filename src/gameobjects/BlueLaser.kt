package gameobjects

import GameObject
import Mesh
import vision.gears.webglmath.Vec2
import vision.gears.webglmath.Vec3
import kotlin.math.cos
import kotlin.math.sin

class BlueLaser(
        mesh: Mesh,
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        private var direction : Float = 0.0f,
        private var lifeTime : Float = 1.0f,
        scale : Vec3 = Vec3(0.0125f, 0.05f, 0.0f)
) : GameObject(mesh, position, roll, scale) {

    init {
        addComponentsAndGatherUniforms(mesh)
        size.set(0.5f, 0.5f)
    }

    override fun interact(
            dt : Float,
            t : Float,
            keysPressed : Set<String>,
            gameObjects : ArrayList<GameObject>,
            mousePosition : Vec2,
            gameObjectsToAdd : ArrayList<GameObject>
    ) : Boolean {
        if (lifeTime < 0.0f)
            return false;

        if (t % 0.2f >= 0.1f)
            offset.x = 0.5f
        else
            offset.x = 0.0f

        position.x -= cos(direction) * dt * 2.0f;
        position.y -= sin(direction) * dt * 2.0f;

        lifeTime -= dt;

        return true;
    }
}
