export class Empresa {
    constructor(
        public id?: number,
        public nombre?: string,
        public domicilio?: string,
        public telefono?: string,
        public categoria?: string,
        public clienteId?: number,
        public userId?: number,
    ) {
    }
}
