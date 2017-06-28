
const enum Talla {
    'XS',
    'S',
    'M',
    'L',
    'XL',
    'XXL'

};

const enum TipoProducto {
    'CAMISA',
    'AMBO_LISO',
    'AMBO_RAYADO',
    'PANTALON',
    'SACO_SPORT',
    'CORBATA'

};

const enum Ubicacion {
    'PERCHERO',
    'MUEBLE',
    'REPISA',
    'DEPOSITO'

};
export class Producto {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public cantidad?: number,
        public codigoOrigen?: string,
        public precioCompra?: number,
        public precioVenta?: number,
        public margenGanancia?: number,
        public talle?: Talla,
        public categoria?: TipoProducto,
        public ubicacion?: Ubicacion,
        public imagenContentType?: string,
        public imagen?: any,
        public detalleFactPresId?: number,
        public proveedorId?: number,
        // transient
        public cantidadSeleccionada?: number,
        public totalFila?: number,
    ) {
    }
}
