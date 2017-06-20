export class Proveedor {
    constructor(
        public id?: number,
        public nombre?: string,
        public domicilio?: string,
        public telefono?: string,
        public cuilCuit?: string,
        public email?: string,
        public celular?: string,
        public productoId?: number,
    ) {
    }
}
