import { BaseEntity } from './../../shared';

export class Rubro implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public proveedors?: BaseEntity[],
    ) {
    }
}
