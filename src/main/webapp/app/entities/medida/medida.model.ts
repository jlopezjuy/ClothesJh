import { BaseEntity } from './../../shared';

const enum TipoFalda {
    'TUBO',
    'RECTA',
    'TAJO',
    'EVASE',
    'CORTEA',
    'CAMPANA',
    'PLATO',
    'GAJOS'
}

const enum TipoMedida {
    'AMBO_HOMBRE',
    'AMBO_MUJER',
    'CHALECO',
    'PANTALON_HOMBRE',
    'PANTALON_MUJER',
    'POLLERA',
    'VESTIDO',
    'CORSET'
}

export class Medida implements BaseEntity {
    constructor(
        public id?: number,
        public contornoBusto?: number,
        public anchoPecho?: number,
        public altoBusto?: number,
        public bajoBusto?: number,
        public alturaPinza?: number,
        public separacionBusto?: number,
        public talleDeltantero?: number,
        public talleEspalda?: number,
        public largoCorset?: number,
        public costado?: number,
        public hombro?: number,
        public anchoHombro?: number,
        public largoManga?: number,
        public sisaDelantero?: number,
        public sisaEspalda?: number,
        public contornoCintura?: number,
        public anteCadera?: number,
        public contornoCadera?: number,
        public posicionCadera?: number,
        public largoFalda?: number,
        public tipoFalda?: TipoFalda,
        public tipoMedida?: TipoMedida,
        public fechaMedida?: any,
        public anchoEspalda?: number,
        public anchoManga?: number,
        public tiroPantalon?: number,
        public anchoPinzaPantalon?: number,
        public anchoRodillaPantalon?: number,
        public botaPantalon?: number,
        public largoPantalon?: number,
        public encargoId?: number,
    ) {
    }
}
