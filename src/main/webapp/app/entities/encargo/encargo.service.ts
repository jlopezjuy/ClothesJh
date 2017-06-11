import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Encargo } from './encargo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EncargoService {

    private resourceUrl = 'api/encargos';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(encargo: Encargo): Observable<Encargo> {
        const copy = this.convert(encargo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(encargo: Encargo): Observable<Encargo> {
        const copy = this.convert(encargo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Encargo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.fechaEncargo = this.dateUtils
            .convertLocalDateFromServer(entity.fechaEncargo);
        entity.fechaEntrega = this.dateUtils
            .convertLocalDateFromServer(entity.fechaEntrega);
    }

    private convert(encargo: Encargo): Encargo {
        const copy: Encargo = Object.assign({}, encargo);
        copy.fechaEncargo = this.dateUtils
            .convertLocalDateToServer(encargo.fechaEncargo);
        copy.fechaEntrega = this.dateUtils
            .convertLocalDateToServer(encargo.fechaEntrega);
        return copy;
    }
}
