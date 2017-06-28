import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Rubro } from './rubro.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RubroService {

    private resourceUrl = 'api/rubros';

    constructor(private http: Http) { }

    create(rubro: Rubro): Observable<Rubro> {
        const copy = this.convert(rubro);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(rubro: Rubro): Observable<Rubro> {
        const copy = this.convert(rubro);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Rubro> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(rubro: Rubro): Rubro {
        const copy: Rubro = Object.assign({}, rubro);
        return copy;
    }
}
