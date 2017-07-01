import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    RubroService,
    RubroPopupService,
    RubroComponent,
    RubroDetailComponent,
    RubroDialogComponent,
    RubroPopupComponent,
    RubroDeletePopupComponent,
    RubroDeleteDialogComponent,
    rubroRoute,
    rubroPopupRoute,
    RubroResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...rubroRoute,
    ...rubroPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RubroComponent,
        RubroDetailComponent,
        RubroDialogComponent,
        RubroDeleteDialogComponent,
        RubroPopupComponent,
        RubroDeletePopupComponent,
    ],
    entryComponents: [
        RubroComponent,
        RubroDialogComponent,
        RubroPopupComponent,
        RubroDeleteDialogComponent,
        RubroDeletePopupComponent,
    ],
    providers: [
        RubroService,
        RubroPopupService,
        RubroResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesRubroModule {}
