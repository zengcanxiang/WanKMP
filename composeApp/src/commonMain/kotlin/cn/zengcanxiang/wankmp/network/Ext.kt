package cn.zengcanxiang.wankmp.network

//fun <E, D1, D2> Operation<E, D1>.mapSuccess(
//    transform: (D1) -> D2
//): Operation<E, D2> = when (this) {
//    is Operation.Error -> this
//    is Operation.Loading -> this
//    is Operation.Ok -> Operation.Ok(
//        data = transform(this.data)
//    )
//}
//
//fun <E, E2, D> Operation<E, D>.mapError(
//    transform: (E) -> E2
//): Operation<E2, D> = when (this) {
//    is Operation.Error -> Operation.Error(
//        error = transform(this.error)
//    )
//    is Operation.Loading -> this
//    is Operation.Ok -> this
//}