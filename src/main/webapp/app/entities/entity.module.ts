import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClothesEmpresaModule } from './empresa/empresa.module';
import { ClothesClienteModule } from './cliente/cliente.module';
import { ClothesModeloModule } from './modelo/modelo.module';
import { ClothesMedidaModule } from './medida/medida.module';
import { ClothesEncargoModule } from './encargo/encargo.module';
import { ClothesPagoModule } from './pago/pago.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClothesEmpresaModule,
        ClothesClienteModule,
        ClothesModeloModule,
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
