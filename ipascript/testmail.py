import smtplib
import email.utils
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText

def send_password(mail, username, password, name):

    msg_text = open('msgText.txt', 'r').read().split('{split}')

    Body = MIMEText(msg_text[0] + username + msg_text[1] + password + msg_text[2])
    msg = MIMEMultipart()
    msg['To'] = email.utils.formataddr((name, mail))
    msg['From'] = email.utils.formataddr(('IT-Drift', 'it-drift@studentmediene.no'))
    msg['Subject'] = 'Brukerkonto'
    msg['Reply-to'] = 'bruker@studentmediene.no'
    msg.attach(Body)

    server = smtplib.SMTP('mail.studentmediene.local')
    server.sendmail('it-drift@studentmediene.no', [mail], msg.as_string())
    server.quit()

send_password("boye.borg@gmail.com","brukernavn","passord","Boye Borg Nygaard")

