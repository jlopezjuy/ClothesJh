import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EmpresaComponent } from './empresa.component';
import { EmpresaDetailComponent } from './empresa-detail.component';
import { EmpresaPopupComponent } from './empresa-dialog.component';
import { EmpresaDeletePopupComponent } from './empresa-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class EmpresaResolvePagingParams implements Resolve<any> {

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

export const empresaRoute: Routes = [
    {
        path: 'empresa',
        component: EmpresaComponent,
        resolve: {
            'pagingParams': EmpresaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.empresa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'empresa/:id',
        component: EmpresaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.empresa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const empresaPopupRoute: Routes = [
    {
        path: 'empresa-new',
        component: EmpresaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.empresa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'empresa/:id/edit',
        component: EmpresaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.empresa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'empresa/:id/delete',
        component: EmpresaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.empresa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
