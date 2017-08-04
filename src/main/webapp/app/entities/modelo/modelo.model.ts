import { BaseEntity } from './../../shared';

export class Modelo implements BaseEntity {
    constructor(
        public id?: number,
        public imagenContentType?: string,
        public imagen?: any,
        public nombreModelo?: string,
        public colorVestido?: string,
        public bordado?: boolean,
        public descripcion?: string,
        public observacion?: string,
        public encargoId?: number,
    ) {
        this.bordado = false;
    }
}
