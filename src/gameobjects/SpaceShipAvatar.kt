package gameobjects

import GameObject
import Mesh
import vision.gears.webglmath.Vec2
import vision.gears.webglmath.Vec3
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class SpaceShipAvatar(
        mesh: Mesh,
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        scale : Vec3 = Vec3(0.1f, 0.1f, 0.0f)
) : GameObject(mesh, position, roll, scale) {

    init {
        addComponentsAndGatherUniforms(mesh)
    }

    var direction = 0.0f
    var speed = 0.0f

    val cooldownTime = 0.48f
    var nextShootTime = 0.0f;

    fun shoot(): BlueLaser {
        return BlueLaser(
                position - Vec3(cos(direction)*0.1f, sin(direction)*0.1f, 0.0f),
                roll,
                direction,
                2.0f
        )
    }

    override fun interact(
            dt : Float,
            t : Float,
            keysPressed : Set<String>,
            gameObjects : ArrayList<GameObject>,
            mousePosition : Vec2,
            gameObjectsToAdd : ArrayList<GameObject>
    ) : Boolean {
        roll = atan2(mousePosition.x, mousePosition.y) + PI.toFloat()
        direction = atan2(mousePosition.x, mousePosition.y) + PI.toFloat() / 2.0f

        if (keysPressed.contains("SPACE")) {
            if (speed < 1.0f)
                speed += dt / 10.0f
            if (speed > 1.0f)
                speed = 1.0f;
        } else {
            if (speed > 0.0f)
                speed -= dt / 3.0f
            if (speed < 0.0f)
                speed = 0.0f;
        }

        if (keysPressed.contains("B")) {
            if (t > nextShootTime) {
                gameObjectsToAdd.add(shoot())
                nextShootTime = t + cooldownTime
            }
        }

        position.x -= cos(direction) * speed * dt * 2.0f;
        position.y -= sin(direction) * speed * dt * 2.0f;
        return true;
    }
}