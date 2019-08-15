FROM python:3.7-alpine


WORKDIR /app


ADD requirements.txt /app/requirements.txt
RUN pip install -r requirements.txt


ADD . /app
EXPOSE 80

CMD ["gunicorn", "app:app", "-b", "0.0.0.0:80", "--log-file", "-", "--access-logfile", "-","--keep-alive", "0"]
