package gameobjects

import GameObject
import meshes.SpaceShipMesh
import vision.gears.webglmath.Vec3

class SpaceShipAvatar(
        mesh: SpaceShipMesh,
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        scale : Vec3 = Vec3(0.1f, 0.1f, 0.0f)
) : GameObject(mesh, position, roll, scale) {

    init {
        addComponentsAndGatherUniforms(mesh)
    }

    override fun move(
            dt : Float,
            t : Float,
            keysPressed : Set<String>,
            gameObjects : List<GameObject>
    ) : Boolean {
        if (keysPressed.contains("W"))
            position.y += dt;
        else if (keysPressed.contains("S"))
            position.y -= dt;
        if (keysPressed.contains("D"))
            position.x += dt;
        else if (keysPressed.contains("A"))
            position.x -= dt;

        return true;
    }
}