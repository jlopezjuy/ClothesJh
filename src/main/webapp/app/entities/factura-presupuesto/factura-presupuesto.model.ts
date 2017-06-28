
const enum FormaPago {
    'CONTADO',
    'TARJETA'

};
export class FacturaPresupuesto {
    constructor(
        public id?: number,
        public fecha?: any,
        public formaPago?: FormaPago,
        public importeTotal?: number,
        public detalleFactPresId?: number,
        public clienteId?: number,
    ) {
    }
}
