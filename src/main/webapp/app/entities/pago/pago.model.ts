export class Pago {
    constructor(
        public id?: number,
        public fechaPago?: any,
        public importe?: number,
        public detalle?: string,
        public numeroRecibo?: number,
        public encargoId?: number,
    ) {
    }
}
