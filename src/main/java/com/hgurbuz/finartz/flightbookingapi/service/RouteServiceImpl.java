package com.hgurbuz.finartz.flightbookingapi.service;

import com.hgurbuz.finartz.flightbookingapi.constants.ExceptionConstant;
import com.hgurbuz.finartz.flightbookingapi.converter.RouteConverter;
import com.hgurbuz.finartz.flightbookingapi.exception.AirportDoesntExistException;
import com.hgurbuz.finartz.flightbookingapi.exception.NotFoundException;
import com.hgurbuz.finartz.flightbookingapi.model.request.RouteRequest;
import com.hgurbuz.finartz.flightbookingapi.model.response.RouteResponse;
import com.hgurbuz.finartz.flightbookingapi.repository.RouteRepository;
import com.hgurbuz.finartz.flightbookingapi.utils.RouteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final RouteUtil routeUtil;
    @Override
    public RouteResponse addRoute(RouteRequest routeRequest) {
        if(routeUtil.isAirportExistFromRouteRequest(routeRequest)){
            var route = RouteConverter.convert(routeRequest);
            return RouteResponse.of(routeRepository.save(route));
        }else{
            throw new AirportDoesntExistException(ExceptionConstant.AIRPORT_DOESNT_EXIST_MESSAGE);
        }


    }

    @Override
    public RouteResponse updateRoute(Long id, RouteRequest routeRequest) {
        RouteResponse routeResponse = findRouteById(id);
        routeResponse.setDepartureCity(routeRequest.getDepartureCity());
        routeResponse.setArrivalCity(routeRequest.getArrivalCity());
        return RouteResponse.of(routeRepository.save(RouteConverter.convert(routeResponse)));
    }

    @Override
    public List<RouteResponse> findAllRoutes() {
        return RouteResponse.of(routeRepository.findAll());
    }

    @Override
    public RouteResponse findRouteById(Long id) {
        var optionalRoute = routeRepository.findById(id);
        if(optionalRoute.isPresent()){
            return RouteResponse.of(optionalRoute.get());
        }else{
            throw new NotFoundException(ExceptionConstant.ROUTE_NOT_FOUND_MESSAGE + id);
        }
    }

    @Override
    public void deleteRouteById(Long id) {
        routeRepository.deleteById(id);
    }
}
