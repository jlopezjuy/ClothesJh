import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ModeloEncargoComponent } from './modelo-encargo.component';
import { ModeloEncargoDetailComponent } from './modelo-encargo-detail.component';
import { ModeloEncargoPopupComponent } from './modelo-encargo-dialog.component';
import { ModeloEncargoDeletePopupComponent } from './modelo-encargo-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ModeloEncargoResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

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

export const modeloEncargoRoute: Routes = [
    {
        path: 'modelo-encargo',
        component: ModeloEncargoComponent,
        resolve: {
            'pagingParams': ModeloEncargoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modeloEncargo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'modelo-encargo/:id',
        component: ModeloEncargoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modeloEncargo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modeloEncargoPopupRoute: Routes = [
    {
        path: 'modelo-encargo-new',
        component: ModeloEncargoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modeloEncargo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo-encargo/:id/edit',
        component: ModeloEncargoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modeloEncargo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo-encargo/:id/delete',
        component: ModeloEncargoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modeloEncargo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
