package gameobjects

import Mesh
import vision.gears.webglmath.Vec3

class MeteorBig(
        mesh: Mesh,
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        scale : Vec3 = Vec3(0.1f, 0.1f, 0.0f)
) : Meteor(mesh, position, roll, scale) {

    init {
        addComponentsAndGatherUniforms(mesh)
    }
}