import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClothesClienteModule } from './cliente/cliente.module';
import { ClothesModeloEncargoModule } from './modelo-encargo/modelo-encargo.module';
import { ClothesMedidaModule } from './medida/medida.module';
import { ClothesEncargoModule } from './encargo/encargo.module';
import { ClothesPagoModule } from './pago/pago.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClothesClienteModule,
        ClothesModeloEncargoModule,
        ClothesMedidaModule,
        ClothesEncargoModule,
        ClothesPagoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesEntityModule {}
