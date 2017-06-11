import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EncargoDetailComponent } from '../../../../../../main/webapp/app/entities/encargo/encargo-detail.component';
import { EncargoService } from '../../../../../../main/webapp/app/entities/encargo/encargo.service';
import { Encargo } from '../../../../../../main/webapp/app/entities/encargo/encargo.model';

describe('Component Tests', () => {

    describe('Encargo Management Detail Component', () => {
        let comp: EncargoDetailComponent;
        let fixture: ComponentFixture<EncargoDetailComponent>;
        let service: EncargoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [EncargoDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EncargoService,
                    EventManager
                ]
            }).overrideTemplate(EncargoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EncargoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EncargoService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Encargo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.encargo).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
