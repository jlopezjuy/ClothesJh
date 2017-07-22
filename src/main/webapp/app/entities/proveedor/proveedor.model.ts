import { BaseEntity } from './../../shared';

export class Proveedor implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public domicilio?: string,
        public telefono?: string,
        public cuilCuit?: string,
        public email?: string,
        public celular?: string,
        public productos?: BaseEntity[],
        public rubroId?: number,
    ) {
    }
}
