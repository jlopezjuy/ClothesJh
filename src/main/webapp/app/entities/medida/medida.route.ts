import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MedidaComponent } from './medida.component';
import { MedidaDetailComponent } from './medida-detail.component';
import { MedidaPopupComponent } from './medida-dialog.component';
import { MedidaDeletePopupComponent } from './medida-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class MedidaResolvePagingParams implements Resolve<any> {

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

export const medidaRoute: Routes = [
    {
        path: 'medida',
        component: MedidaComponent,
        resolve: {
            'pagingParams': MedidaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.medida.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'medida/:id',
        component: MedidaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.medida.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medidaPopupRoute: Routes = [
    {
        path: 'medida-new',
        component: MedidaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.medida.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medida/:id/edit',
        component: MedidaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.medida.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'medida/:id/delete',
        component: MedidaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.medida.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
