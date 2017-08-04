import { BaseEntity } from './../../shared';

export class Cliente implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public apellido?: string,
        public celular?: string,
        public telefono?: string,
        public email?: string,
        public domicilio?: string,
        public colegio?: string,
        public encargos?: BaseEntity[],
        public facturaPresupuestos?: BaseEntity[],
    ) {
    }
}
