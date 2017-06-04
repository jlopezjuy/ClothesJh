export class Cliente {
    constructor(
        public id?: number,
        public nombre?: string,
        public apellido?: string,
        public celular?: string,
        public telefono?: string,
        public email?: string,
        public domicilio?: string,
        public colegio?: string,
        public modeloId?: number,
        public medidaId?: number,
        public encargoId?: number,
        public empresaId?: number,
    ) {
    }
}
