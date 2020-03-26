package gameobjects

import Mesh
import vision.gears.webglmath.Vec3

class MeteorMed(
        mesh: Mesh,
        position : Vec3 = Vec3.zeros.clone(),
        roll : Float = 0.0f,
        scale : Vec3 = Vec3(0.05f, 0.05f, 0.0f)
) : Meteor(mesh, position, roll, scale) {

    init {
        addComponentsAndGatherUniforms(mesh)
    }
}