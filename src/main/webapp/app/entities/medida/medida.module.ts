import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    MedidaService,
    MedidaPopupService,
    MedidaComponent,
    MedidaDetailComponent,
    MedidaDialogComponent,
    MedidaPopupComponent,
    MedidaDeletePopupComponent,
    MedidaDeleteDialogComponent,
    medidaRoute,
    medidaPopupRoute,
    MedidaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...medidaRoute,
    ...medidaPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MedidaComponent,
        MedidaDetailComponent,
        MedidaDialogComponent,
        MedidaDeleteDialogComponent,
        MedidaPopupComponent,
        MedidaDeletePopupComponent,
    ],
    entryComponents: [
        MedidaComponent,
        MedidaDialogComponent,
        MedidaPopupComponent,
        MedidaDeleteDialogComponent,
        MedidaDeletePopupComponent,
    ],
    providers: [
        MedidaService,
        MedidaPopupService,
        MedidaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesMedidaModule {}
