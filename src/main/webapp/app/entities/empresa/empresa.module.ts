import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClothesSharedModule } from '../../shared';
import { ClothesAdminModule } from '../../admin/admin.module';
import {
    EmpresaService,
    EmpresaPopupService,
    EmpresaComponent,
    EmpresaDetailComponent,
    EmpresaDialogComponent,
    EmpresaPopupComponent,
    EmpresaDeletePopupComponent,
    EmpresaDeleteDialogComponent,
    empresaRoute,
    empresaPopupRoute,
    EmpresaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...empresaRoute,
    ...empresaPopupRoute,
];

@NgModule({
    imports: [
        ClothesSharedModule,
        ClothesAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EmpresaComponent,
        EmpresaDetailComponent,
        EmpresaDialogComponent,
        EmpresaDeleteDialogComponent,
        EmpresaPopupComponent,
        EmpresaDeletePopupComponent,
    ],
    entryComponents: [
        EmpresaComponent,
        EmpresaDialogComponent,
        EmpresaPopupComponent,
        EmpresaDeleteDialogComponent,
        EmpresaDeletePopupComponent,
    ],
    providers: [
        EmpresaService,
        EmpresaPopupService,
        EmpresaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClothesEmpresaModule {}
