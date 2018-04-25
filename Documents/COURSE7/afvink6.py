def main():
    seq = OpenFile()
    perc = Calculate(seq)
    html(perc, seq)


def OpenFile():
    file = open('C:\\Users\\Gebruiker\\Documents\\COURSE7\\CDSsalmonella.txt', 'r')
    file = file.readlines()
    seq = ''
    for line in file:
        line = line.strip('\n')
        if '>' not in line:
            seq += line
    return seq


def Calculate(seq):
    length = len(seq)
    c = seq.count("C")
    g = seq.count("G")
    perc = ((c + g) / length) * 100
    return perc

def html(perc, seq):

    req.content_type = 'text/html'
    req.write("<html>"
              "<head>"
              "<title>Afvinkopdracht 6</title>"
              "</head>"
              "<body bgcolor=#2EFE9A>"
              "<br>"
              "<center> titel </center>"
              "<center><form action='afvink6.7.py'>"
              "<input type ='text' name = dna>"
              "<input type = submit>"
              "</form></center>"
              "<hr>"
              "<br>"
              "<center>Eiwitsequentie:</center>"
              "<center>"
              "<body>"
              "</html>")


main()
