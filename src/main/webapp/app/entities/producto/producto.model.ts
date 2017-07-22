import { BaseEntity } from './../../shared';

const enum Talla {
    'XS',
    'S',
    'M',
    'L',
    'XL',
    'XXL'
}

const enum TipoProducto {
    'CAMISA',
    'AMBO_LISO',
    'AMBO_RAYADO',
    'PANTALON',
    'SACO_SPORT',
    'CORBATA',
    'ACCESORIOS',
    'BLUSA',
    'MONO',
    'CORSET',
    'VESTIDO',
    'RAMO',
    'TOCADO',
    'GEMELOS',
    'ZAPATO',
    'LIGAS'
}

const enum Ubicacion {
    'VIDRIERA',
    'PERCHERO',
    'MUEBLE',
    'REPISA',
    'DEPOSITO',
    'MOSTRADOR',
    'ESTANTE'
}

export class Producto implements BaseEntity {
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
        public detalleFactPres?: BaseEntity[],
        public proveedorId?: number,
        // transient
        public cantidadSeleccionada?: number,
        public totalFila?: number,
    ) {
    }
}
