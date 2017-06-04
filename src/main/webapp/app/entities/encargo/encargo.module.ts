import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    EncargoService,
    EncargoPopupService,
    EncargoComponent,
    EncargoDetailComponent,
    EncargoDialogComponent,
    EncargoPopupComponent,
    EncargoDeletePopupComponent,
    EncargoDeleteDialogComponent,
    encargoRoute,
    encargoPopupRoute,
    EncargoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...encargoRoute,
    ...encargoPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EncargoComponent,
        EncargoDetailComponent,
        EncargoDialogComponent,
        EncargoDeleteDialogComponent,
        EncargoPopupComponent,
        EncargoDeletePopupComponent,
    ],
    entryComponents: [
        EncargoComponent,
        EncargoDialogComponent,
        EncargoPopupComponent,
        EncargoDeleteDialogComponent,
        EncargoDeletePopupComponent,
    ],
    providers: [
        EncargoService,
        EncargoPopupService,
        EncargoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesEncargoModule {}
