import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    ModeloEncargoService,
    ModeloEncargoPopupService,
    ModeloEncargoComponent,
    ModeloEncargoDetailComponent,
    ModeloEncargoDialogComponent,
    ModeloEncargoPopupComponent,
    ModeloEncargoDeletePopupComponent,
    ModeloEncargoDeleteDialogComponent,
    modeloEncargoRoute,
    modeloEncargoPopupRoute,
    ModeloEncargoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...modeloEncargoRoute,
    ...modeloEncargoPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ModeloEncargoComponent,
        ModeloEncargoDetailComponent,
        ModeloEncargoDialogComponent,
        ModeloEncargoDeleteDialogComponent,
        ModeloEncargoPopupComponent,
        ModeloEncargoDeletePopupComponent,
    ],
    entryComponents: [
        ModeloEncargoComponent,
        ModeloEncargoDialogComponent,
        ModeloEncargoPopupComponent,
        ModeloEncargoDeleteDialogComponent,
        ModeloEncargoDeletePopupComponent,
    ],
    providers: [
        ModeloEncargoService,
        ModeloEncargoPopupService,
        ModeloEncargoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesModeloEncargoModule {}
