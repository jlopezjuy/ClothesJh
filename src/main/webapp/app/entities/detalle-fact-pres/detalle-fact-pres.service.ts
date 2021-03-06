import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DetalleFactPres } from './detalle-fact-pres.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DetalleFactPresService {

    private resourceUrl = 'api/detalle-fact-pres';
    private resourceUrlFactura = 'api/detalle-fact-pres/factura';
    private resourceSearchUrl = 'api/_search/detalle-fact-pres';

    constructor(private http: Http) { }

    create(detalleFactPres: DetalleFactPres): Observable<DetalleFactPres> {
        const copy = this.convert(detalleFactPres);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(detalleFactPres: DetalleFactPres): Observable<DetalleFactPres> {
        const copy = this.convert(detalleFactPres);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DetalleFactPres> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryFactura(facturaPresupuestoId?: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlFactura}/${facturaPresupuestoId}`)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(detalleFactPres: DetalleFactPres): DetalleFactPres {
        const copy: DetalleFactPres = Object.assign({}, detalleFactPres);
        return copy;
    }
}
