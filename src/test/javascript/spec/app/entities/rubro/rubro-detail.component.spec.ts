import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RubroDetailComponent } from '../../../../../../main/webapp/app/entities/rubro/rubro-detail.component';
import { RubroService } from '../../../../../../main/webapp/app/entities/rubro/rubro.service';
import { Rubro } from '../../../../../../main/webapp/app/entities/rubro/rubro.model';

describe('Component Tests', () => {

    describe('Rubro Management Detail Component', () => {
        let comp: RubroDetailComponent;
        let fixture: ComponentFixture<RubroDetailComponent>;
        let service: RubroService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [RubroDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RubroService,
                    JhiEventManager
                ]
            }).overrideTemplate(RubroDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RubroDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RubroService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Rubro(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rubro).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
