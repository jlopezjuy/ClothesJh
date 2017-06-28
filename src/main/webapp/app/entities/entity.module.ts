import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClothesClienteModule } from './cliente/cliente.module';
import { ClothesModeloModule } from './modelo/modelo.module';
import { ClothesMedidaModule } from './medida/medida.module';
import { ClothesEncargoModule } from './encargo/encargo.module';
import { ClothesPagoModule } from './pago/pago.module';
import { ClothesFacturaPresupuestoModule } from './factura-presupuesto/factura-presupuesto.module';
import { ClothesDetalleFactPresModule } from './detalle-fact-pres/detalle-fact-pres.module';
import { ClothesProductoModule } from './producto/producto.module';
import { ClothesProveedorModule } from './proveedor/proveedor.module';
import { ClothesRubroModule } from './rubro/rubro.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClothesClienteModule,
        ClothesModeloModule,
        ClothesMedidaModule,
        ClothesEncargoModule,
        ClothesPagoModule,
        ClothesFacturaPresupuestoModule,
        ClothesDetalleFactPresModule,
        ClothesProductoModule,
        ClothesProveedorModule,
        ClothesRubroModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesEntityModule {}
