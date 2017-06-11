
const enum Estado {
    'ENCARGADO',
    'CORTADO',
    'PROBADO',
    'ENTREGADO',
    'CANCELADO'

};

const enum TipoEncargo {
    'QUINCE',
    'NOVIA',
    'MADRINA',
    'FIESTA',
    'POLICIA',
    'COMUNION',
    'CONFIRMACION',
    'PAISANA'

};

const enum TipoVestido {
    'DOS_PIEZAS',
    'UNA_PIEZA',
    'SIRENA',
    'CORTE_A'

};
export class Encargo {
    constructor(
        public id?: number,
        public importeTotal?: number,
        public fechaEncargo?: any,
        public fechaEntrega?: any,
        public detalleVestido?: string,
        public estado?: Estado,
        public tipoEncargo?: TipoEncargo,
        public tipoVestido?: TipoVestido,
        public medidaId?: number,
        public pagoId?: number,
        public modeloEncargoId?: number,
        public clienteId?: number,
    ) {
    }
}
