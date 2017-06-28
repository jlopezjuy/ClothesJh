import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    ModeloService,
    ModeloPopupService,
    ModeloComponent,
    ModeloDetailComponent,
    ModeloDialogComponent,
    ModeloPopupComponent,
    ModeloDeletePopupComponent,
    ModeloDeleteDialogComponent,
    modeloRoute,
    modeloPopupRoute,
    ModeloResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...modeloRoute,
    ...modeloPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ModeloComponent,
        ModeloDetailComponent,
        ModeloDialogComponent,
        ModeloDeleteDialogComponent,
        ModeloPopupComponent,
        ModeloDeletePopupComponent,
    ],
    entryComponents: [
        ModeloComponent,
        ModeloDialogComponent,
        ModeloPopupComponent,
        ModeloDeleteDialogComponent,
        ModeloDeletePopupComponent,
    ],
    providers: [
        ModeloService,
        ModeloPopupService,
        ModeloResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesModeloModule {}
