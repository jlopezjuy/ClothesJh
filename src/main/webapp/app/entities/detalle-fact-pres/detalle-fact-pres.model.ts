import { BaseEntity } from './../../shared';

export class DetalleFactPres implements BaseEntity {
    constructor(
        public id?: number,
        public cantidad?: number,
        public predio?: number,
        public facturaPresupuestoId?: number,
        public productoId?: number,
    ) {
    }
}
