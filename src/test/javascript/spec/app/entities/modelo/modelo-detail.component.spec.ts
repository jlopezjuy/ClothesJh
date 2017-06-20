import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ModeloDetailComponent } from '../../../../../../main/webapp/app/entities/modelo/modelo-detail.component';
import { ModeloService } from '../../../../../../main/webapp/app/entities/modelo/modelo.service';
import { Modelo } from '../../../../../../main/webapp/app/entities/modelo/modelo.model';

describe('Component Tests', () => {

    describe('Modelo Management Detail Component', () => {
        let comp: ModeloDetailComponent;
        let fixture: ComponentFixture<ModeloDetailComponent>;
        let service: ModeloService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [ModeloDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ModeloService,
                    JhiEventManager
                ]
            }).overrideTemplate(ModeloDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Modelo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.modelo).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
