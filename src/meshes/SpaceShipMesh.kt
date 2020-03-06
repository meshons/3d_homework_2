package meshes

import Mesh
import Program
import Texture2D
import TexturedMaterial
import WebGL2RenderingContext
import vision.gears.webglmath.Geometry

class SpaceShipMesh(
        gl : WebGL2RenderingContext,
        program : Program,
        geometry: Geometry,
        material: TexturedMaterial = TexturedMaterial(
                program,
                Texture2D(gl, "media/SpaceShooterRedux/PNG/playerShip2_orange.png")
        )
) : Mesh(material, geometry){

    init{
        addComponentsAndGatherUniforms(material, geometry)
    }
}