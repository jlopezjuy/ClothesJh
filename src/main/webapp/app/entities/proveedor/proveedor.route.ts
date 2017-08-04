import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProveedorComponent } from './proveedor.component';
import { ProveedorDetailComponent } from './proveedor-detail.component';
import { ProveedorPopupComponent } from './proveedor-dialog.component';
import { ProveedorDeletePopupComponent } from './proveedor-delete-dialog.component';

@Injectable()
export class ProveedorResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const proveedorRoute: Routes = [
    {
        path: 'proveedor',
        component: ProveedorComponent,
        resolve: {
            'pagingParams': ProveedorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'proveedor/:id',
        component: ProveedorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const proveedorPopupRoute: Routes = [
    {
        path: 'proveedor-new',
        component: ProveedorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proveedor/:id/edit',
        component: ProveedorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'proveedor/:id/delete',
        component: ProveedorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
