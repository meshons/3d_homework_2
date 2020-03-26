package gameobjects

import GameObject
import Mesh
import vision.gears.webglmath.Vec2
import vision.gears.webglmath.Vec3

abstract class Meteor(
        mesh: Mesh,
        position : Vec3,
        roll : Float,
        scale : Vec3
) : GameObject(mesh, position, roll, scale) {

    init {
        addComponentsAndGatherUniforms(mesh)
    }

    override fun interact(
            dt : Float,
            t : Float,
            keysPressed : Set<String>,
            gameObjects : ArrayList<GameObject>,
            mousePosition : Vec2,
            gameObjectsToAdd : ArrayList<GameObject>
    ) : Boolean {
        var hit = false
        gameObjects.forEach {
            if (it != this && hit(it))
                hit = true
        }

        if (hit)
            gameObjectsToAdd.add(
                    Boom(
                            position.clone(),
                            roll,
                            scale.clone()
                    )
            )

        roll += dt/3.0f

        return !hit
    }
}