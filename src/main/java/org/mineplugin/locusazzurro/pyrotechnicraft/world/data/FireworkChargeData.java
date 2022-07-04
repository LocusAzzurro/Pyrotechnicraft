package org.mineplugin.locusazzurro.pyrotechnicraft.world.data;

public class FireworkChargeData {

    /*
    todo: shapes
    sphere: size + point number (default: random force jitter) (option: uniform, jitter)
    circle: size + point number + rotation angles (starting at perpendicular to mov vector, default: + random rotation jitter) (abs rotation starting at parallel to Oxz)
    burst: size(force) + point number + rotation angles (starting at aligned to mov vector) (abs rotation starting at towards positive y)
    2d matrix: size + coords array + y rotation (starting at perpendicular to mov xz) (abs rotation starting at parallel to Oxy)
    3d matrix: size + coords array + rotations
    visitor: take shape and transform into points offsets
    need initial angle data for some shapes + random
    Tag: Shape, ShapeData(compound), Force, Trail, Flicker, Colors(array)
    output: point array, trail flicker colors array
    detect shape tag then invoke related constructor
     */
}