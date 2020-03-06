package gameobjects

import GameObject
import meshes.SpaceShipMesh
import vision.gears.webglmath.Vec3

class SpaceShipAvatar(
        mesh: SpaceShipMesh,
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        scale : Vec3 = Vec3.ones.clone()
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
        return true;
    }
}