import { BaseEntity } from './../../shared';

const enum FormaPago {
    'CONTADO',
    'TARJETA'
}

export class FacturaPresupuesto implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public formaPago?: FormaPago,
        public importeTotal?: number,
        public detalleFactPres?: BaseEntity[],
        public clienteId?: number,
    ) {
    }
}
