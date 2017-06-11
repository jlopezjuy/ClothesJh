import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ModeloEncargoDetailComponent } from '../../../../../../main/webapp/app/entities/modelo-encargo/modelo-encargo-detail.component';
import { ModeloEncargoService } from '../../../../../../main/webapp/app/entities/modelo-encargo/modelo-encargo.service';
import { ModeloEncargo } from '../../../../../../main/webapp/app/entities/modelo-encargo/modelo-encargo.model';

describe('Component Tests', () => {

    describe('ModeloEncargo Management Detail Component', () => {
        let comp: ModeloEncargoDetailComponent;
        let fixture: ComponentFixture<ModeloEncargoDetailComponent>;
        let service: ModeloEncargoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [ModeloEncargoDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ModeloEncargoService,
                    EventManager
                ]
            }).overrideTemplate(ModeloEncargoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeloEncargoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeloEncargoService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ModeloEncargo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.modeloEncargo).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
