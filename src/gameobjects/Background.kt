package gameobjects

import GameObject
import Mesh
import vision.gears.webglmath.Vec2
import vision.gears.webglmath.Vec3

class Background(
        mesh: Mesh,
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        scale : Vec3 = Vec3(0.1f, 0.1f, 0.0f)
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

        return true;
    }
}