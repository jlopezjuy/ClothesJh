import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import {
    ProductoService,
    ProductoPopupService,
    ProductoComponent,
    ProductoDetailComponent,
    ProductoDialogComponent,
    ProductoPopupComponent,
    ProductoDeletePopupComponent,
    ProductoDeleteDialogComponent,
    productoRoute,
    productoPopupRoute,
    ProductoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productoRoute,
    ...productoPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductoComponent,
        ProductoDetailComponent,
        ProductoDialogComponent,
        ProductoDeleteDialogComponent,
        ProductoPopupComponent,
        ProductoDeletePopupComponent,
    ],
    entryComponents: [
        ProductoComponent,
        ProductoDialogComponent,
        ProductoPopupComponent,
        ProductoDeleteDialogComponent,
        ProductoDeletePopupComponent,
    ],
    providers: [
        ProductoService,
        ProductoPopupService,
        ProductoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesProductoModule {}
