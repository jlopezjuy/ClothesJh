import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    PagoService,
    PagoPopupService,
    PagoComponent,
    PagoDetailComponent,
    PagoDialogComponent,
    PagoPopupComponent,
    PagoDeletePopupComponent,
    PagoDeleteDialogComponent,
    pagoRoute,
    pagoPopupRoute,
    PagoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pagoRoute,
    ...pagoPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PagoComponent,
        PagoDetailComponent,
        PagoDialogComponent,
        PagoDeleteDialogComponent,
        PagoPopupComponent,
        PagoDeletePopupComponent,
    ],
    entryComponents: [
        PagoComponent,
        PagoDialogComponent,
        PagoPopupComponent,
        PagoDeleteDialogComponent,
        PagoDeletePopupComponent,
    ],
    providers: [
        PagoService,
        PagoPopupService,
        PagoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesPagoModule {}
